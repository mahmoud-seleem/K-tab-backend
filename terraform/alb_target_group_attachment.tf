resource "aws_alb_target_group_attachment" "test-hosts-tg-hosts" {
  count = 2 # This might be passed as a variable 
  target_group_arn = "${aws_alb_target_group.default.arn}"
  target_id        =  aws_autoscaling_group.asg.id
  port = 80 
}

