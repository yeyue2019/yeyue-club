//package club.yeyue.maven.elasticsearch.demo.document;
//
//import lombok.Data;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.FieldType;
//
///**
// * @author fred
// * @date 2021-06-29 17:43
// */
//@Data
//@Document(indexName = "my_document")
//public class MyDocument {
//
//    @Id
//    private Long id;
//
//    @Field(type = FieldType.Text, analyzer = "ik_max_word")
//    private String title;
//
//    @Field(type = FieldType.Keyword)
//    private String category;
//
//    @Field(type = FieldType.Keyword)
//    private String brand;
//
//    @Field(type = FieldType.Double)
//    private Double price;
//
//    @Field(index = false, type = FieldType.Keyword)
//    private String images; // 图片地址l;
//
//}
