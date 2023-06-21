# Tanzu Application Platform (TAP) Fun

This is a simple Spring Boot application along with a workload definition that
can be deployed in TAP.

## Step-by-Step

 - Clone this repository
 - Create a cluster with TAP installed
 - Ensure that you have the `tanzu` command line tool installed
 - Create and switch into a "my-apps" namespace:

```
$ kubectl create ns my-apps
$ kubectl config set-context --current --namespace=my-apps
```

 - From the root of the locally-cloned repository, apply the workload:

```
$ tanzu apps workload apply -f config/workload.yaml \
      --local-path ./ \
      --source-image habuma/spring-fun:0.0.1-SNAPSHOT \
      --yes
```

 - Check status of the workload (use with `watch` to watch the progress):

```
$ tanzu apps workload get spring-fun
```

 - Optionally, just get a list of workloads and wait for "Ready" status:

```
$ tanzu apps workload list
```

 - Once all "Supply Chain" entries are "Ready", make a request to the app:

```
$ curl http://spring-fun.my-apps.[CLUSTER NAME].tapdemo.vmware.com/books
```

## Binding a Secret to the Workload

 - Create a `Secret` resource (the "type" property is required):

```
$ kubectl create secret generic spring-fun-config \
    --from-literal greeting.message="Hiya"
    --from-literal type=secret
```

 - Create a service claim to the `Secret`:

```
$ tanzu services claims create spring-fun-secret-claim \
    --resource-name spring-fun-config \
    --resource-namespace my-apps \
    --resource-kind Secret \
    --resource-api-version v1
```

 - Check the service claim for readiness:

```
$ tanzu services resource-claims get spring-fun-secret-claim --namespace my-apps
```

 - Update the workload to reference the service claim:

```
$ tanzu apps workload apply spring-fun \
    --service-ref "springfunsecrets=services.apps.tanzu.vmware.com/v1alpha1:ResourceClaim:spring-fun-secret-claim"
```

 - Wait and watch for the update to be applied:

```
$ watch tanzu apps workload get spring-fun
```

 - Verify that the "greeting.message" property from the `Secret` appears in the `/actuator/env` endpoint. You should see something like this near the top:

```
{
    "name": "kubernetesServiceBindingFlattened",
    "properties": {
        "k8s.bindings.springfunsecrets.greeting.message": {
            "value": "Hiya"
        }
    }
},
```
