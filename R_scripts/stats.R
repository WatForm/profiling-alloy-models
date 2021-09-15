library(moments)
getmode <- function(v) {
  uniqv <- unique(v)
  uniqv[which.max(tabulate(match(v, uniqv)))]
}

getStats <- function(fileName) {
  
  values <- scan(fileName)
  result.mean <- mean(values)
  
  result.max <- max(values)
  result.min <- min(values)
  
  cleanValues <- values[values != 0]
  result.mode <- getmode(values)
  result.nzMode <- getmode(cleanValues)
  result.median <- median(values)
  result.nzMedian <- median(cleanValues)
  tot <- sum(values)
  print(sprintf("Skewness: %.1f", skewness(values)))
  print(sprintf("Sum: %.1f", tot))
  print(sprintf("Mean: %.1f", result.mean))
  print(sprintf("Mode: %.1f", result.mode))
  print(sprintf("Non-zero Mode: %.1f", result.nzMode))
  print(sprintf("Median: %.1f", result.median))
  print(sprintf("Non-zero Median: %.1f", result.nzMedian))
  print(sprintf("Min: %.1f", result.min))
  print(sprintf("Max: %.1f", result.max))
  
  print(quantile(values, probs = c(.125,.25, .5, .75, .875)))

  hist(log(values[values<1000]), breaks="FD", xlab = "log(Length)", main = "Length of Alloy Models in Logarithmic Scale")
}

getNonZeroStats <- function(fileName) {
  values <- scan(fileName)
  cleanValues <- values[values != 0]

  result.mean <- mean(cleanValues)

  result.max <- max(cleanValues)
  result.min <- min(cleanValues)

  result.mode <- getmode(cleanValues)
  result.median <- median(cleanValues)
  tot <- sum(cleanValues)

  print("Non-zero stats: ")
  print(sprintf("Sum: %.1f", tot))
  print(sprintf("Mean: %.1f", result.mean))
  print(sprintf("Mode: %i", result.mode))
  print(sprintf("Median: %i", result.median))
  print(sprintf("Min: %i", result.min))
  print(sprintf("Max: %i", result.max))

  print(quantile(cleanValues, probs = c(.125,.25, .5, .75, .875)))
}

#Charcteristics Of Models
characs <- "Results\\CharacteristicsOfModels\\"

#Length
#getStats(paste0(characs,"Length\\length.txt"))

#Signatures
#getStats(paste0(characs, "Signatures\\fieldSigsRatio.txt"))

#Formulas
#getStats(paste0(characs, "Formulas\\formulaCount_expanded.txt"))

#Contraints
getStats(paste0(characs, "Constraints\\sigFacts.txt"))

#CornerCases
#getNonZeroStats(paste0(characs, "CornerCases\\idenModels.txt"))

#Patters of Use
patterns <- "Results\\PatternsOfUse\\"

#Modules
#getStats(paste0(patterns, "Modules\\.txt"))

#Integers
#getStats(paste0(patterns, "Integers\\cardIntSigs.txt"))

#Sets
#getNonZeroStats(paste0(patterns, "Sets\\subDepth.txt"))
#getStats(paste0(patterns, "Sets\\SCG.txt"))

#Scopes
#getStats(paste0(patterns, "Scopes\\intScopeNums.txt"))

#Analysis Complexity
complexity <- "Results\\AnalysisComplexity\\"

#Second-order Operators
#getStats(paste0(complexity, "SecondOrderOperators\\setCard.txt"))

#Partial Total Functions
#getStats(paste0(complexity, "PartialTotalFunctions\\partialFuncs.txt"))

#Depth of Joins and Quantification
#getStats(paste0(complexity, "DepthJoinQuantification\\DOJ.txt"))

#Field Arity
#getStats(paste0(complexity, "FieldArity\\arity.txt"))





