package com.creditharmony.loan.borrow.serve.view;



public class ContractFileIdAndFileNameView  {
	private static final long serialVersionUID = -9061426735736834117L;
	private String docId;			//下载PDF文件的id
	private String fileName;//名件名称
	private String contractFileName;
	
	public String getContractFileName() {
		return contractFileName;
	}
	public void setContractFileName(String contractFileName) {
		this.contractFileName = contractFileName;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
