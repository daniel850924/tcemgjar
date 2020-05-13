package br.gov.mg.tcemg.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class Criptografia {
                
                /**
                * Computa o hash SHA de uma String.
                *
                * @param str
                *            String para calcular o hash.
                * @return Retorna a String hexadecimal do hash computado.
                */
                public static String sha1(String str) {
                               try {
                                               MessageDigest sha1 = MessageDigest.getInstance("SHA");
                                               sha1.reset();
                                               byte[] hash = sha1.digest(str.getBytes());
                                               StringBuffer buffer = new StringBuffer();
                                               for (int i = 0; i < hash.length; i++) {
                                                               buffer.append(Integer.toHexString(0xff & hash[i]));
                                               }
                                               return buffer.toString();
                               } catch (Throwable t) {
                                               return "";
                               }
                }


                /**
                * Computa o hash MD5 de uma String.
                *
                * @param str
                *            String para calcular o hash.
                * @return Retorna a String hexadecimal do hash compudado.
                */
				public static String md5(String senha) {
					String sen = "";
					MessageDigest md = null;
					try {
						md = MessageDigest.getInstance("MD5");
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					}
					BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
					sen = hash.toString(16);
					int cont = sen.length();
					String zero = "";
					while (cont < 32) {
						zero = zero + "0";
						cont++;
					}
					sen = zero + sen;
					return sen;
				}



                /**
                * Este método codifica uma String usando o algoritmo Base64.
                *
                * @param str
                *            String para ser codificada.
                * @return Retorna a String codificada.
                */
                public static String codificarBase64(String str) {
                               return new BASE64Encoder().encode(str.getBytes());
                }

                /**
                * Este método decodifica uma String em Base64.
                *
                * @param str
                *            String para ser decodificada.
                * @return Retorna a String decodificada.
                */
                public static String decodificarBase64(String str) {
                               try {
                                               return new String(new BASE64Decoder().decodeBuffer(str));
                               } catch (IOException e) {
                                               return "";
                               }
                }
                
                public static String createCRC(Object obj) throws IOException, NoSuchAlgorithmException {
                               byte[] bytes = carregarBytes(obj);
                               return converterBytesParaString(check(bytes));
                }
                
                public static String converterBytesParaString(final byte[] bytes) {
                               String result = "";
                               for (int i = 0; i < bytes.length; i++)
                                               result = bytes[i] + result;
                               return result;
                }

                public static byte[] check(byte[] bytes) throws NoSuchAlgorithmException {
                               MessageDigest md = MessageDigest.getInstance("SHA1");
                               md.update(bytes);
                               return md.digest();
                }

                public static byte[] carregarBytes(Object obj) throws IOException {
                               ByteArrayOutputStream bos = new ByteArrayOutputStream();
                               ObjectOutputStream oos = new ObjectOutputStream(bos);
                               oos.writeObject(obj);
                               oos.flush();
                               oos.close();
                               bos.close();
                               byte[] data = bos.toByteArray();
                               return data;
                }
                
}
