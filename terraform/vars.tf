# vars.tf

variable "aws_region" {  
  type        = string  
  description = "Region to use"  
  default     = "us-east-1"  
}  
variable "aws_access_key_id" {  
  type        = string  
  sensitive = true
}  
variable "aws_secret_access_key" {  
  type        = string  
  sensitive = true
}  
variable "aws_zones" {  
  type        = list(string)  
  description = "List of availability zones to use"  
  default     = ["us-east-1a", "us-east-1b"]  
}  
variable "aws_ecr_repo" {  
  type        = string 
  description = "AWS ECR repo containing the docker image needed" 
}

variable "aws_rds_username" {  
  type        = string 
  sensitive = true
}

variable "aws_rds_password" {  
  type        = string 
  sensitive = true
}