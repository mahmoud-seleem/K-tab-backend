resource "aws_db_parameter_group" "backend" {
  name   = "backend-production"
  family = "postgres14"

  parameter {
    name  = "log_connections"
    value = "1"
  }
}