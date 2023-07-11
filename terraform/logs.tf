# logs.tf

resource "aws_cloudwatch_log_group" "k-tab-backend-log-group" {  
  name              = "/ecs/k-tab-backend-production"  
  retention_in_days = 30  
}  
resource "aws_cloudwatch_log_stream" "k-tab-backend-log-stream" {  
  name           = "k-tab-backend-production-log-stream"  
  log_group_name = aws_cloudwatch_log_group.k-tab-backend-log-group.name  
}