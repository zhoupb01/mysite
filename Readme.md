# 正在开发中...

服务熔断降级(暂时不用)

定时发布，延时发布(暂时不用)

在上传图片时，使用tess4j识别图片中的文字，并使用DFA算法对图片中的文字进行敏感词审核。 识别率较低，暂时不用


---
# 特点
* 项目使用Spring Cloud开发，在Gateway中进行统一鉴权，使用OpenFeign进行远程调用。 
* 文件上传，使用MinIO进行文件存储。
* 异步对文章进行审核（敏感词审核，使用DFA算法）。
* 使用Elasticsearch作为搜索引擎。使用MyBatis流式查询将数据库数据导入到ElasticSearch中。
* 使用异步的方式，用mongodb保存用户搜索记录。
* 根据用户输入的关键字展示联想词。
  联想词数据怎么来？1. 自己维护一份，通过分析用户搜索记录，计算出联想词。2. 通过搜索引擎的联想词接口获取。例如5118.com
- [ ] 用户行为分析，通过分析用户的行为，计算出热点文章(阅读：1，点赞：3，评论：5，收藏：8，后面的数字是权重。通过加权计算出总分值)。 
