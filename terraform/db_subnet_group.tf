resource "aws_db_subnet_group" "db_subnet_group" {  
  name       = "db-subnet-group-backend-production"  
  subnet_ids = aws_subnet.private_subnet.*.id  
  tags = {  
    Name               = "db-subnet-group"  
    Env                = "production"  
  }  
}