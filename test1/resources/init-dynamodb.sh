#!/bin/bash

# UserDetail tables config
user_details_table_name="dynamodb-collection-uams-test-user-access-details"
user_details_table_hash_key="id"
user_details_table_account_dormancy_gsi="account_dormancy_index"
account_dormancy_gsi_partition_key="client_id_account_status"
account_dormancy_gsi_range_key="last_login_time"

# UserAccountStatusChangeHistory table config
user_account_status_change_history_table_name="dynamodb-collection-uams-test-user-account-status-change-history"
user_account_status_change_history_hash_key="user_id"
user_account_status_change_history_range_key="account_status_updation_time"

awslocal dynamodb create-table \
    --table-name "$user_details_table_name" \
    --key-schema AttributeName="$user_details_table_hash_key",KeyType=HASH \
    --attribute-definitions AttributeName="$user_details_table_hash_key",AttributeType=S \
                            AttributeName="$account_dormancy_gsi_partition_key",AttributeType=S \
                            AttributeName="$account_dormancy_gsi_range_key",AttributeType=N \
    --provisioned-throughput ReadCapacityUnits=30,WriteCapacityUnits=30 \
        --global-secondary-indexes \
            "[
                {
                    \"IndexName\": \"$user_details_table_account_dormancy_gsi\",
                    \"KeySchema\": [{\"AttributeName\":\"$account_dormancy_gsi_partition_key\",\"KeyType\":\"HASH\"},
                                    {\"AttributeName\":\"$account_dormancy_gsi_range_key\",\"KeyType\":\"RANGE\"}],
                    \"Projection\":{
                        \"ProjectionType\":\"ALL\"
                    },
                    \"ProvisionedThroughput\": {
                        \"ReadCapacityUnits\": 30,
                        \"WriteCapacityUnits\": 30
                    }
                }
            ]" \

awslocal dynamodb create-table \
    --table-name "$user_account_status_change_history_table_name" \
    --key-schema AttributeName="$user_account_status_change_history_hash_key",KeyType=HASH \
                 AttributeName="$user_account_status_change_history_range_key",KeyType=RANGE \
    --attribute-definitions AttributeName="$user_account_status_change_history_hash_key",AttributeType=S \
                            AttributeName="$user_account_status_change_history_range_key",AttributeType=N \
    --provisioned-throughput ReadCapacityUnits=30,WriteCapacityUnits=30 \

echo "Executed init-dynamodb.sh"