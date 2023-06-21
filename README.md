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
