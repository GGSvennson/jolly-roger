HibernateWebApp
===========

Hibernate Primefaces webapp.

Requirements
------------
    - NetBeans 8.0.1
    - Latest Java (At least 1.7, 1.8 is supported)
    - MySQL 5.6.17
    - Tomcat 8.0.9 TomEE

This project is the implementation of a web site using Hibernate and Primefaces. It was developed in NetBeans IDE 8.0.1
and uses MySQL as a database.

Hibernate is implemented using Hibernate HQL and Criteria. Both have previously been tested to validate proper operation.
The website is installed on http://www.josealvarezdelara.com/HibernateWebApp-0.0.1-SNAPSHOT/ where you can
try how it works.

There are two kinds of users, the Administrator and the Users. The Administrator can operate on the site while the
Users can only view. The site was deployed in Tomcat 8.0.9 TomEE.

Captcha is implemented as an input component with a built-in validator that is integrated with reCaptcha. reCAPTCHA is a free service to protect your website from spam and abuse provided by Google - See more at: https://www.google.com/recaptcha/intro/index.html
