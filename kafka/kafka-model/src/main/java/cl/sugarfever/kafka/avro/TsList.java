/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package cl.sugarfever.kafka.avro;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class TsList extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -3893524237303109263L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TsList\",\"namespace\":\"cl.sugarfever.kafka.avro\",\"fields\":[{\"name\":\"Tses\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"Ts\",\"fields\":[{\"name\":\"id_ts\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\",\"logicalType\":\"UUID\"}},{\"name\":\"idpagina\",\"type\":\"int\"},{\"name\":\"sexo\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"default\":\"\"},{\"name\":\"edad\",\"type\":\"int\",\"default\":0},{\"name\":\"nombre\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"default\":\"\"},{\"name\":\"medidas\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"default\":\"\"},{\"name\":\"seccion\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"default\":\"\"},{\"name\":\"depilacion\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"default\":\"\"},{\"name\":\"ubicacion\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"default\":\"\"},{\"name\":\"horario\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"default\":\"\"},{\"name\":\"descripcion\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"default\":\"\"},{\"name\":\"atencion\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"default\":\"\"},{\"name\":\"telefono\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"default\":\"0000 0000\"},{\"name\":\"estatura\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"valor\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"default\":\"\"},{\"name\":\"video\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"default\":\"\"},{\"name\":\"imagen\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"default\":\"\"},{\"name\":\"idjob\",\"type\":\"long\",\"default\":0},{\"name\":\"sitio\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"default\":\"\"},{\"name\":\"cartel\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"default\":\"\"},{\"name\":\"ciudad\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"default\":\"\"},{\"name\":\"servicios\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"Servicio\",\"fields\":[{\"name\":\"idservicio\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\",\"logicalType\":\"UUID\"}},{\"name\":\"nombre\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"adicional\",\"type\":\"boolean\"}]},\"java-class\":\"java.util.List\"},\"default\":[]},{\"name\":\"imagenes\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"Imagen\",\"fields\":[{\"name\":\"idimagen\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\",\"logicalType\":\"UUID\"}},{\"name\":\"url\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]},\"java-class\":\"java.util.List\"},\"default\":[]},{\"name\":\"fecharegistro\",\"type\":\"long\",\"default\":0,\"logicalType\":\"timestamp-millis\"}]},\"java-class\":\"java.util.List\"}}],\"version\":\"1\"}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<TsList> ENCODER =
      new BinaryMessageEncoder<TsList>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<TsList> DECODER =
      new BinaryMessageDecoder<TsList>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<TsList> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<TsList> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<TsList> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<TsList>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this TsList to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a TsList from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a TsList instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static TsList fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private java.util.List<cl.sugarfever.kafka.avro.Ts> Tses;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public TsList() {}

  /**
   * All-args constructor.
   * @param Tses The new value for Tses
   */
  public TsList(java.util.List<cl.sugarfever.kafka.avro.Ts> Tses) {
    this.Tses = Tses;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return Tses;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: Tses = (java.util.List<cl.sugarfever.kafka.avro.Ts>)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'Tses' field.
   * @return The value of the 'Tses' field.
   */
  public java.util.List<cl.sugarfever.kafka.avro.Ts> getTses() {
    return Tses;
  }


  /**
   * Sets the value of the 'Tses' field.
   * @param value the value to set.
   */
  public void setTses(java.util.List<cl.sugarfever.kafka.avro.Ts> value) {
    this.Tses = value;
  }

  /**
   * Creates a new TsList RecordBuilder.
   * @return A new TsList RecordBuilder
   */
  public static cl.sugarfever.kafka.avro.TsList.Builder newBuilder() {
    return new cl.sugarfever.kafka.avro.TsList.Builder();
  }

  /**
   * Creates a new TsList RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new TsList RecordBuilder
   */
  public static cl.sugarfever.kafka.avro.TsList.Builder newBuilder(cl.sugarfever.kafka.avro.TsList.Builder other) {
    if (other == null) {
      return new cl.sugarfever.kafka.avro.TsList.Builder();
    } else {
      return new cl.sugarfever.kafka.avro.TsList.Builder(other);
    }
  }

  /**
   * Creates a new TsList RecordBuilder by copying an existing TsList instance.
   * @param other The existing instance to copy.
   * @return A new TsList RecordBuilder
   */
  public static cl.sugarfever.kafka.avro.TsList.Builder newBuilder(cl.sugarfever.kafka.avro.TsList other) {
    if (other == null) {
      return new cl.sugarfever.kafka.avro.TsList.Builder();
    } else {
      return new cl.sugarfever.kafka.avro.TsList.Builder(other);
    }
  }

  /**
   * RecordBuilder for TsList instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TsList>
    implements org.apache.avro.data.RecordBuilder<TsList> {

    private java.util.List<cl.sugarfever.kafka.avro.Ts> Tses;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(cl.sugarfever.kafka.avro.TsList.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.Tses)) {
        this.Tses = data().deepCopy(fields()[0].schema(), other.Tses);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
    }

    /**
     * Creates a Builder by copying an existing TsList instance
     * @param other The existing instance to copy.
     */
    private Builder(cl.sugarfever.kafka.avro.TsList other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.Tses)) {
        this.Tses = data().deepCopy(fields()[0].schema(), other.Tses);
        fieldSetFlags()[0] = true;
      }
    }

    /**
      * Gets the value of the 'Tses' field.
      * @return The value.
      */
    public java.util.List<cl.sugarfever.kafka.avro.Ts> getTses() {
      return Tses;
    }


    /**
      * Sets the value of the 'Tses' field.
      * @param value The value of 'Tses'.
      * @return This builder.
      */
    public cl.sugarfever.kafka.avro.TsList.Builder setTses(java.util.List<cl.sugarfever.kafka.avro.Ts> value) {
      validate(fields()[0], value);
      this.Tses = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'Tses' field has been set.
      * @return True if the 'Tses' field has been set, false otherwise.
      */
    public boolean hasTses() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'Tses' field.
      * @return This builder.
      */
    public cl.sugarfever.kafka.avro.TsList.Builder clearTses() {
      Tses = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TsList build() {
      try {
        TsList record = new TsList();
        record.Tses = fieldSetFlags()[0] ? this.Tses : (java.util.List<cl.sugarfever.kafka.avro.Ts>) defaultValue(fields()[0]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<TsList>
    WRITER$ = (org.apache.avro.io.DatumWriter<TsList>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<TsList>
    READER$ = (org.apache.avro.io.DatumReader<TsList>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    long size0 = this.Tses.size();
    out.writeArrayStart();
    out.setItemCount(size0);
    long actualSize0 = 0;
    for (cl.sugarfever.kafka.avro.Ts e0: this.Tses) {
      actualSize0++;
      out.startItem();
      e0.customEncode(out);
    }
    out.writeArrayEnd();
    if (actualSize0 != size0)
      throw new java.util.ConcurrentModificationException("Array-size written was " + size0 + ", but element count was " + actualSize0 + ".");

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      long size0 = in.readArrayStart();
      java.util.List<cl.sugarfever.kafka.avro.Ts> a0 = this.Tses;
      if (a0 == null) {
        a0 = new SpecificData.Array<cl.sugarfever.kafka.avro.Ts>((int)size0, SCHEMA$.getField("Tses").schema());
        this.Tses = a0;
      } else a0.clear();
      SpecificData.Array<cl.sugarfever.kafka.avro.Ts> ga0 = (a0 instanceof SpecificData.Array ? (SpecificData.Array<cl.sugarfever.kafka.avro.Ts>)a0 : null);
      for ( ; 0 < size0; size0 = in.arrayNext()) {
        for ( ; size0 != 0; size0--) {
          cl.sugarfever.kafka.avro.Ts e0 = (ga0 != null ? ga0.peek() : null);
          if (e0 == null) {
            e0 = new cl.sugarfever.kafka.avro.Ts();
          }
          e0.customDecode(in);
          a0.add(e0);
        }
      }

    } else {
      for (int i = 0; i < 1; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          long size0 = in.readArrayStart();
          java.util.List<cl.sugarfever.kafka.avro.Ts> a0 = this.Tses;
          if (a0 == null) {
            a0 = new SpecificData.Array<cl.sugarfever.kafka.avro.Ts>((int)size0, SCHEMA$.getField("Tses").schema());
            this.Tses = a0;
          } else a0.clear();
          SpecificData.Array<cl.sugarfever.kafka.avro.Ts> ga0 = (a0 instanceof SpecificData.Array ? (SpecificData.Array<cl.sugarfever.kafka.avro.Ts>)a0 : null);
          for ( ; 0 < size0; size0 = in.arrayNext()) {
            for ( ; size0 != 0; size0--) {
              cl.sugarfever.kafka.avro.Ts e0 = (ga0 != null ? ga0.peek() : null);
              if (e0 == null) {
                e0 = new cl.sugarfever.kafka.avro.Ts();
              }
              e0.customDecode(in);
              a0.add(e0);
            }
          }
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










