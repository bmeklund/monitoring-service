# Monitoring-service

This service expose a health-endpoint for polling to assure connectivity between and too OpenShift-projects.
Deploy this service on a project together with a ConfigMap for the projectname.
Querying the health-endpoint will result in a http-200 response with status and a description. 
Responseformat: {"status”:”OK/CRITICAL”,”body”:”[PROJECTNAME] Monitoring Ok/Not available"}

### Building 

The project is built with maven and consists of a spring-boot application with rest-endpoints.
There is junit-tests for testing the internal logic too.
To build run:
    mvn clean install

### Running the example in OpenShift

The application is meant to be deployed on top of OpenShift with fabric8 and Source2Image.
The application has dependency to a ConfigMap named "monitoring-config" with the key "openshift.projectname" containing the name of the current OpenShift project(defaults to Projectname-missing if its not there)
The deploy use a maven profile called "openshift"

It is assumed that:
- OpenShift platform is already running
- Your system is configured for Fabric8 Maven Workflow

The example can be built and run on OpenShift using a single goal:

    mvn fabric8:deploy -P openshift

The application has, as mentioned above, a dependency to a ConfigMap for getting the projectname/namespace it is running in.  
Create the ConfigMap manually in OpenShift console or by the command below.  
After creation, make sure it is connected/added to the monitoring-services deployment. 

    oc create configmap monitoring-config --from-literal=openshift.projectname="[the correct projectName]"
        
When the example runs in OpenShift, you can use the OpenShift client tool to inspect the status

To list all the running pods:

    oc get pods

Then find the name of the pod that runs this quickstart, and output the logs from the running pods with:

    oc logs <name of pod>

You can also use the OpenShift console to view status, running pods, and view logs and much more.  

**Readiness** and **LivenessProbe** polls are aimed/connected to the /actuator/health endpoints instead of /health .
Logs are read and the readiness will pollute the logs if aimed at /health.

## Open API specification (Swagger)
The application comes with a swagger-plugin and when run hosts a swagger-ui where you can expore and try the service out.  
The swagger-page can be found here: http://[host].[domain]:[port]/swagger-ui.html  
ex: http://localhost:8080/swagger-ui.html id run locally  
The API-specification can be used for configuring a 3scale-API towards it.

## Application metadata
The application also use Spring-boot actuator to expose more endpoints for metrics and status etc.
Listed below is the available endpoints.

**/actuator/health**

Shows application health information

**/actuator/info**

Displays arbitrary application info

**/actuator/loggers**

the logging level of the application

**/actuator/metrics**

Shows ‘metrics’ information

**/actuator/prometheus**

Exposes metrics in a format that can be scraped by a Prometheus server. 