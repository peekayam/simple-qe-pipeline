#!/bin/bash
if [ $(npm list angular-devkit/build-angular|grep empty -wc)==1 ]
then
        npm install --save-dev @angular-devkit/build-angular
fi
