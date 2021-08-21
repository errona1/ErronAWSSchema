package samples.clickstream.avro;
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class ClickEvent extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"ClickEvent\",\"namespace\":\"samples.clickstream.avro\",\"fields\":[{\"name\":\"ip\",\"type\":\"string\"},{\"name\":\"eventtimestamp\",\"type\":\"long\"},{\"name\":\"devicetype\",\"type\":\"string\"},{\"name\":\"event_type\",\"type\":[\"string\",\"null\"]},{\"name\":\"product_type\",\"type\":[\"string\",\"null\"]},{\"name\":\"userid\",\"type\":\"int\"},{\"name\":\"globalseq\",\"type\":\"long\"},{\"name\":\"prevglobalseq\",\"type\":\"long\",\"default\":0}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.CharSequence ip;
  @Deprecated public long eventtimestamp;
  @Deprecated public java.lang.CharSequence devicetype;
  @Deprecated public java.lang.CharSequence event_type;
  @Deprecated public java.lang.CharSequence product_type;
  @Deprecated public int userid;
  @Deprecated public long globalseq;
  @Deprecated public long prevglobalseq;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>. 
   */
  public ClickEvent() {}

  /**
   * All-args constructor.
   */
  public ClickEvent(java.lang.CharSequence ip, java.lang.Long eventtimestamp, java.lang.CharSequence devicetype, java.lang.CharSequence event_type, java.lang.CharSequence product_type, java.lang.Integer userid, java.lang.Long globalseq, java.lang.Long prevglobalseq) {
    this.ip = ip;
    this.eventtimestamp = eventtimestamp;
    this.devicetype = devicetype;
    this.event_type = event_type;
    this.product_type = product_type;
    this.userid = userid;
    this.globalseq = globalseq;
    this.prevglobalseq = prevglobalseq;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return ip;
    case 1: return eventtimestamp;
    case 2: return devicetype;
    case 3: return event_type;
    case 4: return product_type;
    case 5: return userid;
    case 6: return globalseq;
    case 7: return prevglobalseq;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: ip = (java.lang.CharSequence)value$; break;
    case 1: eventtimestamp = (java.lang.Long)value$; break;
    case 2: devicetype = (java.lang.CharSequence)value$; break;
    case 3: event_type = (java.lang.CharSequence)value$; break;
    case 4: product_type = (java.lang.CharSequence)value$; break;
    case 5: userid = (java.lang.Integer)value$; break;
    case 6: globalseq = (java.lang.Long)value$; break;
    case 7: prevglobalseq = (java.lang.Long)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'ip' field.
   */
  public java.lang.CharSequence getIp() {
    return ip;
  }

  /**
   * Sets the value of the 'ip' field.
   * @param value the value to set.
   */
  public void setIp(java.lang.CharSequence value) {
    this.ip = value;
  }

  /**
   * Gets the value of the 'eventtimestamp' field.
   */
  public java.lang.Long getEventtimestamp() {
    return eventtimestamp;
  }

  /**
   * Sets the value of the 'eventtimestamp' field.
   * @param value the value to set.
   */
  public void setEventtimestamp(java.lang.Long value) {
    this.eventtimestamp = value;
  }

  /**
   * Gets the value of the 'devicetype' field.
   */
  public java.lang.CharSequence getDevicetype() {
    return devicetype;
  }

  /**
   * Sets the value of the 'devicetype' field.
   * @param value the value to set.
   */
  public void setDevicetype(java.lang.CharSequence value) {
    this.devicetype = value;
  }

  /**
   * Gets the value of the 'event_type' field.
   */
  public java.lang.CharSequence getEventType() {
    return event_type;
  }

  /**
   * Sets the value of the 'event_type' field.
   * @param value the value to set.
   */
  public void setEventType(java.lang.CharSequence value) {
    this.event_type = value;
  }

  /**
   * Gets the value of the 'product_type' field.
   */
  public java.lang.CharSequence getProductType() {
    return product_type;
  }

  /**
   * Sets the value of the 'product_type' field.
   * @param value the value to set.
   */
  public void setProductType(java.lang.CharSequence value) {
    this.product_type = value;
  }

  /**
   * Gets the value of the 'userid' field.
   */
  public java.lang.Integer getUserid() {
    return userid;
  }

  /**
   * Sets the value of the 'userid' field.
   * @param value the value to set.
   */
  public void setUserid(java.lang.Integer value) {
    this.userid = value;
  }

  /**
   * Gets the value of the 'globalseq' field.
   */
  public java.lang.Long getGlobalseq() {
    return globalseq;
  }

  /**
   * Sets the value of the 'globalseq' field.
   * @param value the value to set.
   */
  public void setGlobalseq(java.lang.Long value) {
    this.globalseq = value;
  }

  /**
   * Gets the value of the 'prevglobalseq' field.
   */
  public java.lang.Long getPrevglobalseq() {
    return prevglobalseq;
  }

  /**
   * Sets the value of the 'prevglobalseq' field.
   * @param value the value to set.
   */
  public void setPrevglobalseq(java.lang.Long value) {
    this.prevglobalseq = value;
  }

  /** Creates a new ClickEvent RecordBuilder */
  public static samples.clickstream.avro.ClickEvent.Builder newBuilder() {
    return new samples.clickstream.avro.ClickEvent.Builder();
  }
  
  /** Creates a new ClickEvent RecordBuilder by copying an existing Builder */
  public static samples.clickstream.avro.ClickEvent.Builder newBuilder(samples.clickstream.avro.ClickEvent.Builder other) {
    return new samples.clickstream.avro.ClickEvent.Builder(other);
  }
  
  /** Creates a new ClickEvent RecordBuilder by copying an existing ClickEvent instance */
  public static samples.clickstream.avro.ClickEvent.Builder newBuilder(samples.clickstream.avro.ClickEvent other) {
    return new samples.clickstream.avro.ClickEvent.Builder(other);
  }
  
  /**
   * RecordBuilder for ClickEvent instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<ClickEvent>
    implements org.apache.avro.data.RecordBuilder<ClickEvent> {

    private java.lang.CharSequence ip;
    private long eventtimestamp;
    private java.lang.CharSequence devicetype;
    private java.lang.CharSequence event_type;
    private java.lang.CharSequence product_type;
    private int userid;
    private long globalseq;
    private long prevglobalseq;

    /** Creates a new Builder */
    private Builder() {
      super(samples.clickstream.avro.ClickEvent.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(samples.clickstream.avro.ClickEvent.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.ip)) {
        this.ip = data().deepCopy(fields()[0].schema(), other.ip);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.eventtimestamp)) {
        this.eventtimestamp = data().deepCopy(fields()[1].schema(), other.eventtimestamp);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.devicetype)) {
        this.devicetype = data().deepCopy(fields()[2].schema(), other.devicetype);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.event_type)) {
        this.event_type = data().deepCopy(fields()[3].schema(), other.event_type);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.product_type)) {
        this.product_type = data().deepCopy(fields()[4].schema(), other.product_type);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.userid)) {
        this.userid = data().deepCopy(fields()[5].schema(), other.userid);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.globalseq)) {
        this.globalseq = data().deepCopy(fields()[6].schema(), other.globalseq);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.prevglobalseq)) {
        this.prevglobalseq = data().deepCopy(fields()[7].schema(), other.prevglobalseq);
        fieldSetFlags()[7] = true;
      }
    }
    
    /** Creates a Builder by copying an existing ClickEvent instance */
    private Builder(samples.clickstream.avro.ClickEvent other) {
            super(samples.clickstream.avro.ClickEvent.SCHEMA$);
      if (isValidValue(fields()[0], other.ip)) {
        this.ip = data().deepCopy(fields()[0].schema(), other.ip);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.eventtimestamp)) {
        this.eventtimestamp = data().deepCopy(fields()[1].schema(), other.eventtimestamp);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.devicetype)) {
        this.devicetype = data().deepCopy(fields()[2].schema(), other.devicetype);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.event_type)) {
        this.event_type = data().deepCopy(fields()[3].schema(), other.event_type);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.product_type)) {
        this.product_type = data().deepCopy(fields()[4].schema(), other.product_type);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.userid)) {
        this.userid = data().deepCopy(fields()[5].schema(), other.userid);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.globalseq)) {
        this.globalseq = data().deepCopy(fields()[6].schema(), other.globalseq);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.prevglobalseq)) {
        this.prevglobalseq = data().deepCopy(fields()[7].schema(), other.prevglobalseq);
        fieldSetFlags()[7] = true;
      }
    }

    /** Gets the value of the 'ip' field */
    public java.lang.CharSequence getIp() {
      return ip;
    }
    
    /** Sets the value of the 'ip' field */
    public samples.clickstream.avro.ClickEvent.Builder setIp(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.ip = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'ip' field has been set */
    public boolean hasIp() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'ip' field */
    public samples.clickstream.avro.ClickEvent.Builder clearIp() {
      ip = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'eventtimestamp' field */
    public java.lang.Long getEventtimestamp() {
      return eventtimestamp;
    }
    
    /** Sets the value of the 'eventtimestamp' field */
    public samples.clickstream.avro.ClickEvent.Builder setEventtimestamp(long value) {
      validate(fields()[1], value);
      this.eventtimestamp = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'eventtimestamp' field has been set */
    public boolean hasEventtimestamp() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'eventtimestamp' field */
    public samples.clickstream.avro.ClickEvent.Builder clearEventtimestamp() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'devicetype' field */
    public java.lang.CharSequence getDevicetype() {
      return devicetype;
    }
    
    /** Sets the value of the 'devicetype' field */
    public samples.clickstream.avro.ClickEvent.Builder setDevicetype(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.devicetype = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'devicetype' field has been set */
    public boolean hasDevicetype() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'devicetype' field */
    public samples.clickstream.avro.ClickEvent.Builder clearDevicetype() {
      devicetype = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'event_type' field */
    public java.lang.CharSequence getEventType() {
      return event_type;
    }
    
    /** Sets the value of the 'event_type' field */
    public samples.clickstream.avro.ClickEvent.Builder setEventType(java.lang.CharSequence value) {
      validate(fields()[3], value);
      this.event_type = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'event_type' field has been set */
    public boolean hasEventType() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'event_type' field */
    public samples.clickstream.avro.ClickEvent.Builder clearEventType() {
      event_type = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'product_type' field */
    public java.lang.CharSequence getProductType() {
      return product_type;
    }
    
    /** Sets the value of the 'product_type' field */
    public samples.clickstream.avro.ClickEvent.Builder setProductType(java.lang.CharSequence value) {
      validate(fields()[4], value);
      this.product_type = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'product_type' field has been set */
    public boolean hasProductType() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'product_type' field */
    public samples.clickstream.avro.ClickEvent.Builder clearProductType() {
      product_type = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /** Gets the value of the 'userid' field */
    public java.lang.Integer getUserid() {
      return userid;
    }
    
    /** Sets the value of the 'userid' field */
    public samples.clickstream.avro.ClickEvent.Builder setUserid(int value) {
      validate(fields()[5], value);
      this.userid = value;
      fieldSetFlags()[5] = true;
      return this; 
    }
    
    /** Checks whether the 'userid' field has been set */
    public boolean hasUserid() {
      return fieldSetFlags()[5];
    }
    
    /** Clears the value of the 'userid' field */
    public samples.clickstream.avro.ClickEvent.Builder clearUserid() {
      fieldSetFlags()[5] = false;
      return this;
    }

    /** Gets the value of the 'globalseq' field */
    public java.lang.Long getGlobalseq() {
      return globalseq;
    }
    
    /** Sets the value of the 'globalseq' field */
    public samples.clickstream.avro.ClickEvent.Builder setGlobalseq(long value) {
      validate(fields()[6], value);
      this.globalseq = value;
      fieldSetFlags()[6] = true;
      return this; 
    }
    
    /** Checks whether the 'globalseq' field has been set */
    public boolean hasGlobalseq() {
      return fieldSetFlags()[6];
    }
    
    /** Clears the value of the 'globalseq' field */
    public samples.clickstream.avro.ClickEvent.Builder clearGlobalseq() {
      fieldSetFlags()[6] = false;
      return this;
    }

    /** Gets the value of the 'prevglobalseq' field */
    public java.lang.Long getPrevglobalseq() {
      return prevglobalseq;
    }
    
    /** Sets the value of the 'prevglobalseq' field */
    public samples.clickstream.avro.ClickEvent.Builder setPrevglobalseq(long value) {
      validate(fields()[7], value);
      this.prevglobalseq = value;
      fieldSetFlags()[7] = true;
      return this; 
    }
    
    /** Checks whether the 'prevglobalseq' field has been set */
    public boolean hasPrevglobalseq() {
      return fieldSetFlags()[7];
    }
    
    /** Clears the value of the 'prevglobalseq' field */
    public samples.clickstream.avro.ClickEvent.Builder clearPrevglobalseq() {
      fieldSetFlags()[7] = false;
      return this;
    }

    @Override
    public ClickEvent build() {
      try {
        ClickEvent record = new ClickEvent();
        record.ip = fieldSetFlags()[0] ? this.ip : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.eventtimestamp = fieldSetFlags()[1] ? this.eventtimestamp : (java.lang.Long) defaultValue(fields()[1]);
        record.devicetype = fieldSetFlags()[2] ? this.devicetype : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.event_type = fieldSetFlags()[3] ? this.event_type : (java.lang.CharSequence) defaultValue(fields()[3]);
        record.product_type = fieldSetFlags()[4] ? this.product_type : (java.lang.CharSequence) defaultValue(fields()[4]);
        record.userid = fieldSetFlags()[5] ? this.userid : (java.lang.Integer) defaultValue(fields()[5]);
        record.globalseq = fieldSetFlags()[6] ? this.globalseq : (java.lang.Long) defaultValue(fields()[6]);
        record.prevglobalseq = fieldSetFlags()[7] ? this.prevglobalseq : (java.lang.Long) defaultValue(fields()[7]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
