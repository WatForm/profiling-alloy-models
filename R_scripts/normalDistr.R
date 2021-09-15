library(ggplot2)
my_data <- read.table("NOF_Cohesion.txt")
names(my_data)[1] <- "NOF"
#ggqqplot(my_data$NOF)


# boxplot(my_data$NOF, plot=FALSE)$out
# 
# outliers <- boxplot(my_data$NOF, plot=FALSE)$out
# 
# new_data <- my_data
# new_data <- new_data[-which(new_data$NOF %in% outliers),]
# 
# new_data <- as.data.frame(new_data)
# 
# names(new_data)[1] <- "NOF"

nof_plot <- ggplot(data = my_data, breaks = seq(0, 100, 5), aes(x = NOF)) +
  geom_density() 

nof_plot <- nof_plot + scale_x_continuous(name = "#fields / #sigsThatHaveFields",
                                          breaks = seq(0, 100, 5),
                                          limits=c(0, 100))

nof_plot


boxplot(my_data$NOF,data=my_data, main="Field Distribution",
        ylab="Models", xlab="#fields / #sigsThatHaveFields", ylim = c(0,20),
        horizontal = TRUE, outlier.tagging = TRUE)

#shapiro.test(new_data$NOF)



