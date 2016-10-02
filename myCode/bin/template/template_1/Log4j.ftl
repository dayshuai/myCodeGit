#######################################################################
# Categories and levels
#######################################################################
#log4j.appender.stdout=org.apache.log4j.ConsoleAppender
#log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
#log4j.appender.stdout.layout.ConversionPattern=%d %p [%c] - %m%n


#log4j.logger.com.ibatis=debug
#log4j.logger.com.ibatis.common.jdbc.SimpleDataSource=debug
#og4j.logger.com.ibatis.common.jdbc.ScriptRunner=debug
#log4j.logger.com.ibatis.sqlmap.engine.impl.SqlMapClientDelegate=debug
#log4j.logger.java.sql.Connection=debug
#log4j.logger.java.sql.Statement=debug
#log4j.logger.java.sql.PreparedStatement=debug,stdout
log4j.rootCategory=FileApp, ConApp
log4j.category.com.gzedu = debug
log4j.category.com.ibatis = debug
log4j.category.freemarker = error
log4j.category.org.springframework = error
log4j.category.com.opensymphony = info
log4j.category.org.apache = info
log4j.category.java.sql = debug

#######################################################################
# Appenders
#######################################################################

# ConApp is set to be a ConsoleAppender.
log4j.appender.ConApp=org.apache.log4j.ConsoleAppender
# ConApp uses PatternLayout.
log4j.appender.ConApp.layout=org.apache.log4j.PatternLayout
# Define Pattern
log4j.appender.ConApp.layout.ConversionPattern=%p %d{yyyy-MM-dd HH:mm:ss,SSS} %c.%t %m%n

# FileApp
#log4j.appender.FileApp=org.apache.log4j.RollingFileAppender
log4j.appender.FileApp=org.apache.log4j.DailyRollingFileAppender
log4j.appender.FileApp.DatePattern ='_'yyyy-MM-dd'.log'
log4j.appender.FileApp.File=${r"${project}"}WEB-INF/logs/log
log4j.appender.FileApp.layout=org.apache.log4j.PatternLayout
log4j.appender.FileApp.layout.ConversionPattern=%d{yyyy/MM/dd HH:mm:ss} %5p %c{1}:%L - %m%n
log4j.appender.FileApp.Append=true
