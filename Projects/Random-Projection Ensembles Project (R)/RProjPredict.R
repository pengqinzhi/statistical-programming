RProjPredict <-
  function(XTrain #n by p training data matrix
           , YTrain #n vector of the classes of the training samples
           , XTest  #n.test by p test data matrix
           , d = 2 * log2(ncol(XTrain))   #dimension to project into
           , projmethod = "Achlioptas"
  )
  {
    p <- ncol(XTrain)   # the number of features
    n <- nrow(XTrain)
    ntest <- nrow(XTest)
    
    # choose random d for each projection if have multiple d
    if (length(d) != 1) {
      d = sample(d, 1)
    }

    # generate a random matrix
    RP <- RProjGenerate(p, d, method = projmethod)
    
    # generate new projected matrices for training and test set
    XRPTrain <- as.matrix(crossprod(t(XTrain), RP),n,d)
    XRPTest <- as.matrix(crossprod(t(XTest), RP),ntest,d)
    
    # predict by decision tree
    out <- rpart(YTrain~., data.frame(XRPTrain))
    
    Train.class <- predict(out, data.frame(XRPTrain), type = "class")
    Test.prob <- predict(out, data.frame(XRPTest))
    
    return(list(Train.class, Test.prob))
    
  }
