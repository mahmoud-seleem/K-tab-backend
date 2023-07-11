resource "aws_elasticache_subnet_group" "elasticache_subnet" {  
  name       = "k-tab-backend-production-cache-subnet"  
  subnet_ids = aws_subnet.private_subnet.*.id  
}  

resource "aws_elasticache_replication_group" "rep_group" {  
  automatic_failover_enabled    = true  
  preferred_cache_cluster_azs   = var.aws_zones  
  replication_group_id          = "k-tab-backend-production-rep-group"  
  description                   = "Replication group"  
  node_type                     = "cache.t3.micro"  
  num_cache_clusters            = 2  
  port                          = 6379  
  subnet_group_name             = aws_elasticache_subnet_group.elasticache_subnet.name  
  lifecycle {  
    ignore_changes = [num_cache_clusters]  
  }  
}  
resource "aws_elasticache_cluster" "replica" {  
  count                = 2  
  cluster_id           = "k-tab-backend-production-rep-group-${count.index}"  
  replication_group_id = aws_elasticache_replication_group.rep_group.id  
}