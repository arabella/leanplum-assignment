# leanplum-assignment

Simple mobile framework using Appium to execute a test on leanplum rondo app.
Setup:
1. check where appium runs on your machine, open config properties file and update with correct info 
  - appiumHost=http://0.0.0.0:4723/wd/hub
2. open lina.xml and update with deviceName and udid for your device
3. you can run the test by 
  - right clicking the xml file or
  - run gradle --no-build-cache test  -PsuiteFile=lina.xml
