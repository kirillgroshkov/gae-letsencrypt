
# GAE-LetsEncrypt

Allows your Java App Engine project to pass LetsEncrypt challenge validation.

Allows to respond to request like `http://yourdomain.com/.well-known/acme-challenge/ASdfwersdfchallenge`

appcfg.sh doesn't allow to upload folders starting with `.` in their names (like `.well-known`), so
serving such static files is a problem.

Solution - LetsEncryptServlet that handles that for you!

# Instruction

1. Download `gae-letsencrypt.jar`
2. Put it into `WEB-INF/lib`
3. Add servlet to your `web.xml`:

    <servlet>
        <servlet-name>letsencrypt</servlet-name>
        <servlet-class>letsencrypt.LetsEncryptServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>letsencrypt</servlet-name>
        <url-pattern>/.well-known/acme-challenge/*</url-pattern>
    </servlet-mapping>

4. Put your challenge/response file into `WEB-INF/letsencrypt/` folder
5. Profit!

Your app will be able to pass LetsEncrypt validation request!

# Development
## IDE

Exclude:

    .idea
    build
    dist
    out
    war/WEB-INF/lib
    war/WEB-INF/classes

## Build
       
    ant
     
# Credits

Inspired by: http://igorartamonov.com/2015/12/lets-encrypt-ssl-google-appengine/
