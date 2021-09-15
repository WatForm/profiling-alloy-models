getPercentDistr <- function(fileList) {
  total <- 0
  for (f in fileList) {
    values <- scan(f)
    sum_f <- sum(values)
    total <- total + sum_f
  }
  
  for (f in fileList) {
    values <- scan(f)
    sum_p <- sum(values)
    percentage <- (sum_p/total) * 100
    print(sprintf(paste("Percentage of", strsplit(f, "\\.")[[1]][[1]],"%.1f%%"), percentage))
  }
}

getPercentDistr_total <- function(fileList, total) {
  for (f in fileList) {
    values <- scan(f)
    sum_p <- sum(values)
    percentage <- (sum_p/total) * 100
    print(sprintf(paste("Percentage of", strsplit(f, "\\.")[[1]][[1]],"%.1f%%"), percentage))
  }
}

#getPercentDistr(c("Results\\PatternsOfUse\\Modules\\userModules.txt","Results\\PatternsOfUse\\Modules\\boolean.txt", "Results\\PatternsOfUse\\Modules\\integer.txt", "Results\\PatternsOfUse\\Modules\\graph.txt", "Results\\PatternsOfUse\\Modules\\naturals.txt", "Results\\PatternsOfUse\\Modules\\ordering.txt", "Results\\PatternsOfUse\\Modules\\relation.txt", "Results\\PatternsOfUse\\Modules\\ternary.txt", "Results\\PatternsOfUse\\Modules\\time.txt", "Results\\PatternsOfUse\\Modules\\seq.txt", "Results\\PatternsOfUse\\Modules\\seqrel.txt", "Results\\PatternsOfUse\\Modules\\sequence.txt"))
#getPercentDistr_total(c("Results\\PatternsOfUse\\Modules\\userModules_mod.txt","Results\\PatternsOfUse\\Modules\\boolean_mod.txt", "Results\\PatternsOfUse\\Modules\\integer_mod.txt", "Results\\PatternsOfUse\\Modules\\integer_noMod.txt","Results\\PatternsOfUse\\Modules\\graph_mod.txt", "Results\\PatternsOfUse\\Modules\\naturals_mod.txt", "Results\\PatternsOfUse\\Modules\\ordering_mod.txt", "Results\\PatternsOfUse\\Modules\\relation_mod.txt", "Results\\PatternsOfUse\\Modules\\ternary_mod.txt", "Results\\PatternsOfUse\\Modules\\time_mod.txt", "Results\\PatternsOfUse\\Modules\\seq_mod.txt", "Results\\PatternsOfUse\\Modules\\seqrel_mod.txt", "Results\\PatternsOfUse\\Modules\\sequence_mod.txt"), 2138)
#getPercentDistr(c("setExact.txt", "setNonExact.txt", "derivedDefault.txt", "defaultNonExact.txt","derivedExact.txt", "derivedNonExact.txt"))

#getPercentDistr(c("Results\\ModelingPractices\\intConst.txt", "Results\\ModelingPractices\\intDecls.txt"))

#getPercentDistr_total(c("Results\\ModelingPractices\\intNoMod.txt", "Results\\ModelingPractices\\intOpenMod.txt"),1049)
#getPercentDistr(c("Results\\CharacteristicsOfModels\\CornerCases\\none.txt", "Results\\CharacteristicsOfModels\\CornerCases\\univ.txt", "Results\\CharacteristicsOfModels\\CornerCases\\iden.txt"))

getPercentDistr(c("Results\\PatternsOfUse\\Formulas\\domPred.txt","Results\\PatternsOfUse\\Formulas\\domRel.txt", "Results\\PatternsOfUse\\Formulas\\domNav.txt", "Results\\PatternsOfUse\\Formulas\\purePred.txt", "Results\\PatternsOfUse\\Formulas\\pureRel.txt", "Results\\PatternsOfUse\\Formulas\\pureNav.txt"))