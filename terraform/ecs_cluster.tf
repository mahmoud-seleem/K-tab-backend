resource "aws_ecs_cluster" "production" {  
  name = "backend-production"  
  lifecycle {  
    create_before_destroy = true  
  }  
  tags = {  
    Name = "backend-production"  
    Env  = "backend-production"  
  }  
}