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
        // Create a new pooled data source
        PooledDataSource dataSource = new PooledDataSource();

        // Set the driver class name
        dataSource.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        // Set the database URL with the database name and trust server certificate
        dataSource.setUrl("jdbc:sqlserver://localhost\\SQLEXPRESS:50544;database=TestAutomation;trustServerCertificate=true;");

        // Set the username and password explicitly
        dataSource.setUsername("sa");
        dataSource.setPassword("Oreosmoothie11@");

        // Create a transaction factory
        TransactionFactory transactionFactory = new JdbcTransactionFactory();

        // Create an environment with the transaction factory and data source
        Environment environment = new Environment("development", transactionFactory, dataSource);

        // Create a configuration with the environment
        Configuration configuration = new Configuration(environment);

        // Enable camel case mapping for database columns
        configuration.setMapUnderscoreToCamelCase(true);

        // Add the mapper interface
        configuration.addMapper(BookingCaseMapper.class);

        // Build the session factory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        // Open a session with auto-commit enabled
        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        // Return the mapper
        return sqlSession.getMapper(BookingCaseMapper.class);
    }
}