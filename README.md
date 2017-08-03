# Exomiser Demo Application

This is a simple Spring Boot app to demonstrate how to set-up and use the Exomiser programmatically from within another Spring application.

To run it:

```
java -jar exomiser-demo-0.0.1-SNAPSHOT.jar --exomiser.data-directory=/home/user/exomiser-dir/data
```

The application simply reads the file specified by the ``vcf`` path parameter, performs a Phive analysis according to the supplied ``phenotypes`` 
 and returns the results in the specified ``format``. In a browser you can then visit:
 
 ```
 http://localhost:8080/analyse/?vcf=/home/user/Pfeiffer.vcf&phenotypes=HP:0001156,HP:0001363,HP:0011304,HP:0010055&format=HTML
 ```
 
 or you can curl the results in VCF, TSV_GENE or TSV_VARIANT flatfile formats. 
