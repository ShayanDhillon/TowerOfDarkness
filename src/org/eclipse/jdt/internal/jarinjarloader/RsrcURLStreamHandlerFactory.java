/*    */ package org.eclipse.jdt.internal.jarinjarloader;
/*    */ 
/*    */ import java.net.URLStreamHandler;
/*    */ import java.net.URLStreamHandlerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RsrcURLStreamHandlerFactory
/*    */   implements URLStreamHandlerFactory
/*    */ {
/*    */   private ClassLoader classLoader;
/*    */   private URLStreamHandlerFactory chainFac;
/*    */   
/*    */   public RsrcURLStreamHandlerFactory(ClassLoader cl) {
/* 34 */     this.classLoader = cl;
/*    */   }
/*    */ 
/*    */   
/*    */   public URLStreamHandler createURLStreamHandler(String protocol) {
/* 39 */     if ("rsrc".equals(protocol))
/* 40 */       return new RsrcURLStreamHandler(this.classLoader); 
/* 41 */     if (this.chainFac != null)
/* 42 */       return this.chainFac.createURLStreamHandler(protocol); 
/* 43 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setURLStreamHandlerFactory(URLStreamHandlerFactory fac) {
/* 55 */     this.chainFac = fac;
/*    */   }
/*    */ }


/* Location:              C:\Users\shaya\OneDrive\Desktop\Tower Of Darkness.jar!\org\eclipse\jdt\internal\jarinjarloader\RsrcURLStreamHandlerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */