#!/bin/bash

# This file is required due to differences between how openshift imagebuilder treats docker multistage files, and how native docker acts
# Consider the following line:
# COPY --from=build /usr/src/app/target/*-runner /work/application
#
# Native docker will create a single executable called "application" in the work directory
# Imagebuilder will take the approach of creating an application directory underneath work, and putting *-runner into that directory
#
# We need both approaches to work, so we need to work out if "application" is a directory, if it is then we need to take the first
# (and hopefully only) file in there and call it "application"

if [ -d /work/application ]; then
  mv /work/application /work/applicationdir
  mv /work/applicationdir/*-runner /work/application
fi