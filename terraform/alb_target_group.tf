resource "aws_alb_target_group" "default" {  
  name     = "alb-tg-backend-production"  
  port     = 80  
  protocol = "HTTP"  
  vpc_id   = aws_vpc.vpc.id  
 health_check {
    path                = "/"
    protocol            = "HTTP"
    matcher             = "200"
    interval            = 15
    timeout             = 10
    healthy_threshold   = 7
    unhealthy_threshold = 7
  }

  tags = {
    Name = "alb-ai-target-group"
  } 
}