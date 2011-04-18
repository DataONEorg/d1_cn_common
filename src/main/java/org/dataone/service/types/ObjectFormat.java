
package org.dataone.service.types;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://dataone.org/service/types/0.5.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ObjectFormat">
 *   &lt;xs:restriction base="xs:string">
 *     &lt;xs:enumeration value="eml://ecoinformatics.org/eml-2.0.0"/>
 *     &lt;xs:enumeration value="eml://ecoinformatics.org/eml-2.0.1"/>
 *     &lt;xs:enumeration value="eml://ecoinformatics.org/eml-2.1.0"/>
 *     &lt;xs:enumeration value="eml://ecoinformatics.org/eml-2.1.1"/>
 *     &lt;xs:enumeration value="FGDC-STD-001.1-1999"/>
 *     &lt;xs:enumeration value="FGDC-STD-001-1998"/>
 *     &lt;xs:enumeration value="INCITS 453-2009"/>
 *     &lt;xs:enumeration value="http://www.unidata.ucar.edu/namespaces/netcdf/ncml-2.2"/>
 *     &lt;xs:enumeration value="CF-1.0"/>
 *     &lt;xs:enumeration value="CF-1.1"/>
 *     &lt;xs:enumeration value="CF-1.2"/>
 *     &lt;xs:enumeration value="CF-1.3"/>
 *     &lt;xs:enumeration value="CF-1.4"/>
 *     &lt;xs:enumeration value="http://www.cuahsi.org/waterML/1.0/"/>
 *     &lt;xs:enumeration value="http://www.cuahsi.org/waterML/1.1/"/>
 *     &lt;xs:enumeration value="http://www.loc.gov/METS/"/>
 *     &lt;xs:enumeration value="netCDF-3"/>
 *     &lt;xs:enumeration value="netCDF-4"/>
 *     &lt;xs:enumeration value="text/plain"/>
 *     &lt;xs:enumeration value="text/csv"/>
 *     &lt;xs:enumeration value="image/bmp"/>
 *     &lt;xs:enumeration value="image/gif"/>
 *     &lt;xs:enumeration value="image/jp2"/>
 *     &lt;xs:enumeration value="image/jpeg"/>
 *     &lt;xs:enumeration value="image/png"/>
 *     &lt;xs:enumeration value="image/svg+xml"/>
 *     &lt;xs:enumeration value="image/tiff"/>
 *     &lt;xs:enumeration value="http://rs.tdwg.org/dwc/xsd/simpledarwincore/"/>
 *     &lt;xs:enumeration value="http://digir.net/schema/conceptual/darwin/2003/1.0/darwin2.xsd"/>
 *     &lt;xs:enumeration value="application/octet-stream"/>
 *   &lt;/xs:restriction>
 * &lt;/xs:simpleType>
 * </pre>
 */
public enum ObjectFormat {
    EML_2_0_0("eml://ecoinformatics.org/eml-2.0.0"), EML_2_0_1(
            "eml://ecoinformatics.org/eml-2.0.1"), EML_2_1_0(
            "eml://ecoinformatics.org/eml-2.1.0"), EML_2_1_1(
            "eml://ecoinformatics.org/eml-2.1.1"), FGDC_STD_001_1_1999(
            "FGDC-STD-001.1-1999"), FGDC_STD_001_1998("FGDC-STD-001-1998"), INCITS_453_2009(
            "INCITS 453-2009"), NCML_2_2(
            "http://www.unidata.ucar.edu/namespaces/netcdf/ncml-2.2"), CF_1_0(
            "CF-1.0"), CF_1_1("CF-1.1"), CF_1_2("CF-1.2"), CF_1_3("CF-1.3"), CF_1_4(
            "CF-1.4"), WATER_ML_1_0(
            "http://www.cuahsi.org/waterML/1.0/"), WATER_ML_1_1(
            "http://www.cuahsi.org/waterML/1.1/"), DSPACE_METS_SIP_1_0(
            "http://www.loc.gov/METS/"), NET_CDF_3("netCDF-3"), NET_CDF_4(
            "netCDF-4"), TEXT_PLAIN("text/plain"), TEXT_CSV("text/csv"), IMAGE_BMP(
            "image/bmp"), IMAGE_GIF("image/gif"), IMAGE_JP2("image/jp2"), IMAGE_JPEG(
            "image/jpeg"), IMAGE_PNG("image/png"), IMAGE_SVGXML("image/svg+xml"), IMAGE_TIFF(
            "image/tiff"), SIMPLE_DARWIN_CORE(
            "http://rs.tdwg.org/dwc/xsd/simpledarwincore/"), DARWIN_2(
            "http://digir.net/schema/conceptual/darwin/2003/1.0/darwin2.xsd"), OCTET_STREAM(
            "application/octet-stream");
    private final String value;

    private ObjectFormat(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public static ObjectFormat convert(String value) {
        for (ObjectFormat inst : values()) {
            if (inst.toString().equals(value)) {
                return inst;
            }
        }
        return ObjectFormat.OCTET_STREAM;
    }
}
