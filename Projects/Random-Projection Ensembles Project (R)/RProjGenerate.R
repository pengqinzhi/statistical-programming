RProjGenerate <-
  function(p = 100 #higher dimension
           , d = 5   #lower dimension
           , method = "Achlioptas" # Random projection method
           )
  {
    if (p < d) stop("d must be less than p")
    # sparse random projections
    if (method == "Achlioptas") {
      RP <- matrix(sqrt(3)*sample(c(-1,0,1), size=p*d, replace=TRUE, prob=c(1/6,2/3,1/6)), p, d)  
    }
    
    # -1,1 entries
    if (method == "Bernoulli") {
      RP <- matrix(sample(c(1,-1), size=p*d, replace=TRUE, prob=c(0.5,0.5)), p, d)  
    }
    
    # Gaussian distributed projections with i.i.d. N(0,1/p) entries
    if (method == "Gaussian") {
      RP <- matrix(1/sqrt(p)*rnorm(p*d,0,1), p, d)
    }
    
    # standard normal distribution projections
    if (method == "sdnormal") {
      RP <- matrix(rnorm(p*d, 0, 1), p, d)
    }
    
    # Q matrix in QR decomposition for Gaussian distributed projections
    if (method == "Haar") {
      R0 <- matrix(1/sqrt(p)*rnorm(p*d,0,1), p, d)
      RP <- matrix(qr.Q(qr(R0[, 1:d]))[,1:d], p, d)
    }
    
    # uniformly distributed axis aligned projections
    if (method == "axis") {
      S <- sample(1:p, d)
      RP <- matrix(0, p, d)
      for (D in 1:d)
      {
        RP[S[D], D] <- 1
      }
    }
    
    # 0,1 entries with the high probability for zero
    if (method == "onezero") {
      RP <- matrix(sample(c(0,1), size=p*d, replace=TRUE, prob=c(0.99,0.01)), p, d)  
    }
    
    return(RP)
    
  }