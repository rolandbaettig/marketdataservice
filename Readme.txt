If you want to install the database schema, you have to do two things:
    1. create all necessary schemas in your database. The needed schemas are audit, core, mds and usr (CREATE SCHEMA xxx)
    2. start the application with the --spring.profiles.active=development option.