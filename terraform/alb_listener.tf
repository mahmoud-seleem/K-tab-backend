resource "aws_alb_listener" "http" {  
  load_balancer_arn = aws_lb.alb.arn  
  port              = "80"  
  protocol          = "HTTP"  
  depends_on        = [aws_alb_target_group.default]  
  default_action {  
    target_group_arn = aws_alb_target_group.default.arn  
    type             = "forward"  
  }  
}