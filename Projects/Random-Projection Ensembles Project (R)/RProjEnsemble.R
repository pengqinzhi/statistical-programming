RProjEnsemble <-
  function(XTrain #n by p training data matrix
           , YTrain #n vector of the classes of the training samples
           , XTest  #n.test by p test data matrix
           , L = 100       # the size of ensemble
           , d = 2 * log2(ncol(XTrain))   #dimension to project into
           , projmethod = "Achlioptas"
           , clustertype = "Default" #computing cluster type
           , cores = 1
           , machines = NULL  #names of computers on network to form cluster
           , seed = NULL #reproducability seed
           )
  {
    # create workers
    if(clustertype == "Default"){cluster = makePSOCKcluster(1)}
    if(clustertype == "Fork"){cluster = makeForkCluster(cores)}
    if(clustertype == "Socket"){cluster = makePSOCKcluster(names = machines)}
    
    # export the variables from current R instance into the global environment of newly created workers
    clusterExport(cluster, ls(), envir = environment())
    
    # set the seed if there is
    if(is.null(seed) == FALSE) {clusterSetRNGStream(cluster, seed)}
    
    # do parallel to calculate each probability in 1:L
    out <- parLapply(cl = cluster, 1:L, function(i){return(RProjPredict(XTrain, YTrain, XTest, d, projmethod))})
    
    # select the top trees based on training error
    Train.error <- simplify2array(parLapply(cl = cluster, 1:L, function(i){return(mean(out[[i]][[1]] != YTrain))}))
    indice <- which(Train.error <= quantile(Train.error, .5))
    Test.prob <- parLapply(cl = cluster, indice, function(i){return(out[[i]][[2]])})
    
    # sum probability matrices and select labels
    conf <- Reduce("+", Test.prob) / length(indice)
    Test.class <- colnames(conf)[max.col(conf)]
    
    # stop the workers and close the parallel backend
    stopCluster(cluster) 
    return(Test.class) 
  }