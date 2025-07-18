# PizzaStore Deployment Troubleshooting Guide

## Current Issue: "Context failed to start"

This error usually occurs due to missing dependencies. Here's how to fix it:

## Quick Fix (Test Without Database)

The application has been modified to work without SQL Server for testing purposes. It will use mock data if the database is not available.

### Steps to Deploy:

1. **Add Required JAR Files** to `WebContent/WEB-INF/lib/`:
   - **JSTL 1.2**: `jstl-1.2.jar` and `standard-1.1.2.jar`
   - **SQL Server JDBC**: `mssql-jdbc-12.4.2.jre8.jar` (optional for testing)

2. **Download JSTL from Maven Central**:
   ```
   https://repo1.maven.org/maven2/javax/servlet/jstl/1.2/jstl-1.2.jar
   https://repo1.maven.org/maven2/taglibs/standard/1.1.2/standard-1.1.2.jar
   ```

3. **Build and Deploy**:
   ```bash
   # Clean build
   ant clean compile build
   
   # Or manually compile
   javac -cp "WebContent/WEB-INF/lib/*" -d WebContent/WEB-INF/classes src/**/*.java
   ```

4. **Deploy to Tomcat**:
   - Copy `WebContent` folder to Tomcat `webapps`
   - Rename to `ASM` or `PizzaStore`
   - Start Tomcat

## Testing Without Database

The application will work with these default accounts:
- **Admin**: username=`admin`, password=`admin`
- **Customer**: username=`customer1`, password=`123`

Mock pizza data will be displayed if SQL Server is not available.

## Full Database Setup (Optional)

1. **Install SQL Server Express**
2. **Run** `database.sql` script
3. **Update** connection in `src/utils/DBContext.java`
4. **Add** `mssql-jdbc-12.4.2.jre8.jar` to `WebContent/WEB-INF/lib/`

## Common Errors and Solutions

### 1. ClassNotFoundException: javax.servlet.jsp.jstl.core.Config
**Solution**: Add `jstl-1.2.jar` and `standard-1.1.2.jar` to `WEB-INF/lib`

### 2. ClassNotFoundException: com.microsoft.sqlserver.jdbc.SQLServerDriver
**Solution**: Add SQL Server JDBC driver OR ignore (app uses mock data)

### 3. HTTP Status 404
**Solution**: Access via `http://localhost:8080/ASM/` or `http://localhost:8080/ASM/MainController`

### 4. Compilation Errors
**Solution**: Ensure servlet-api.jar is in classpath during compilation

## Project URLs

After successful deployment:
- **Home**: `http://localhost:8080/ASM/`
- **Login**: `http://localhost:8080/ASM/MainController?action=login`
- **Products**: `http://localhost:8080/ASM/MainController?action=products`
- **Admin**: `http://localhost:8080/ASM/MainController?action=adminPage` (login as admin first)

## Minimal Required Files

For basic deployment, you need:
```
WebContent/
├── WEB-INF/
│   ├── lib/
│   │   ├── jstl-1.2.jar          ← REQUIRED
│   │   └── standard-1.1.2.jar    ← REQUIRED
│   ├── classes/                  ← Compiled Java files
│   └── web.xml
├── jsp/                          ← JSP files
└── index.html
```

## NetBeans Specific

If using NetBeans:
1. Right-click project → Properties
2. Libraries → Add JAR/Folder
3. Add JSTL JARs to "Compile" tab
4. Clean and Build project
5. Deploy