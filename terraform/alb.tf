resource "aws_lb" "alb" {  
  name               = "alb-backend-production"  
  load_balancer_type = "application"  
  internal           = false  
  security_groups    = [aws_security_group.alb.id]  
  subnets            = aws_subnet.public_subnet.*.id  
}