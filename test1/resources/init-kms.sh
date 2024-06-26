#!/bin/bash

kms_result=`awslocal kms create-key`
echo "Executed init-kms.sh"