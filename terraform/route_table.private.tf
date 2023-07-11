resource "aws_route_table" "private_route_table" {  
  vpc_id = aws_vpc.vpc.id  
  tags = {  
    Name       = "private-route-table-backend-production"  
    Env        = "production"  
  }  
}