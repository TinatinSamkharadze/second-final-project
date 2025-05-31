package ge.tbc.testautomation.config;

import ge.tbc.testautomation.mappers.BookingCaseMapper;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public class DataBaseConfig {
    public static BookingCaseMapper dbMapper() {
        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:sqlserver://localhost\\SQLEXPRESS:50544;database=TestAutomation;trustServerCertificate=true;");
        dataSource.setUsername("Real_User");
        dataSource.setPassword("RealUser123#");
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.addMapper(BookingCaseMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        return sqlSession.getMapper(BookingCaseMapper.class);
    }
}