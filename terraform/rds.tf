resource "aws_db_instance" "production" {  
  backup_window             = "03:00-04:00"  
  ca_cert_identifier        = "rds-ca-2019"  
  db_subnet_group_name      = aws_db_subnet_group.db_subnet_group.name 
  engine_version            = "14.7"  
  engine                    = "postgres"  
  skip_final_snapshot       = true  
  identifier                = "k-tab-backend-production"  
  instance_class            = "db.t3.micro"  
  maintenance_window        = "sun:08:00-sun:09:00"  
  # name                      = "k-tab-backend-production"  
  parameter_group_name      = aws_db_parameter_group.backend.name  
  password                  = var.aws_rds_password
  username                  = var.aws_rds_username
  allocated_storage         = "10"  
  port                      = "5432"  
  vpc_security_group_ids    = [aws_security_group.rds_sg.id]  
  multi_az                  = false  
  backup_retention_period   = 7  
}