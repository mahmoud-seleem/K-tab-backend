resource "aws_autoscaling_group" "asg" {  
  name                      = "as_group_be"  
  vpc_zone_identifier       = aws_subnet.private_subnet.*.id  
  min_size                  = 1  
  max_size                  = 5  
  desired_capacity          = 2  
  launch_configuration      = aws_launch_configuration.aws_conf.id  
  health_check_type         = "EC2"  
  health_check_grace_period = 120  
  default_cooldown          = 30  
  termination_policies      = ["OldestInstance"]  
  lifecycle {  
    create_before_destroy   = true  
  }  
  tag {  
    key                 = "Env"  
    propagate_at_launch = true  
    value               = "production"  
  }  
  tag {  
    key                 = "Name"  
    propagate_at_launch = true  
    value               = "backend"  
  }  
}