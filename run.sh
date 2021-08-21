export MSK_STACK=MSK
export KAFKA_CLIENT_STACK=MSKClient
export VPC_STACK=$(aws cloudformation describe-stacks --stack-name $MSK_STACK --query 'Stacks[0].Outputs[?OutputKey==`VPCStackName`].OutputValue' --output text)
export SECONDARY_DESERIALIZER=false
export CSR=false
./deploy.sh $VPC_STACK $KAFKA_CLIENT_STACK $MSK_STACK $SECONDARY_DESERIALIZER $CSR
