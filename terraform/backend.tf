terraform {  
  backend "s3" {  
    bucket         = "k-tab-tf-state"  
    key            = "terraform-backend-production.tfstate"  
    region         = "us-east-1"  
    encrypt        = true 
    dynamodb_table = "k-tab-terraform-state-locking"  
  }  
}