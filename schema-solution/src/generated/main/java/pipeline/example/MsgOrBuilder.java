// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: pipeline/example/sample.proto

package pipeline.example;

public interface MsgOrBuilder extends
    // @@protoc_insertion_point(interface_extends:Msg)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>optional string foo = 1;</code>
   */
  java.lang.String getFoo();
  /**
   * <code>optional string foo = 1;</code>
   */
  com.google.protobuf.ByteString
      getFooBytes();

  /**
   * <code>optional .SecondMsg blah = 2;</code>
   */
  boolean hasBlah();
  /**
   * <code>optional .SecondMsg blah = 2;</code>
   */
  pipeline.example.SecondMsg getBlah();
  /**
   * <code>optional .SecondMsg blah = 2;</code>
   */
  pipeline.example.SecondMsgOrBuilder getBlahOrBuilder();
}
