package book;

/**
 * Book_tbl 의 한 행을 나타내는 VO (☆ 학생 구현)
 *
 * <pre>
 * 담당자(@assignee): ___________________
 * 작업단위(@task)  : TASK-C
 * 가이드 카드      : docs/tasks/TASK-C_조원_Book_DBUtil.md
 *
 * 능력단위요소 3 / 수행준거 3.3
 * 체크리스트 3-1 (DAO/VO 캡슐화 — private 필드 + public getter/setter)
 * </pre>
 *
 * <p><b>학생 구현 가이드 (정답 코드 ✗ — 가이드만)</b></p>
 *
 * <p>BookManagementUI 가 호출하는 시그니처는 그대로 유지하고,
 *    <b>필드 선언</b> 과 <b>메서드 본문</b> 만 채우세요.</p>
 *
 * <p>채울 항목:</p>
 * <ol>
 *   <li>private 필드 6개:
 *       <code>bookCode</code>(String, PK),
 *       <code>classificationId</code>(Integer, FK·null 가능),
 *       <code>author</code>(String),
 *       <code>name</code>(String),
 *       <code>publisher</code>(String),
 *       <code>isReserve</code>(String, 'Y'/'N')</li>
 *   <li>전체 인자 생성자 본문 — 모든 필드 초기화</li>
 *   <li>getter / setter 12개 본문</li>
 *   <li>toString() — 모든 필드를 한 줄로 표시</li>
 * </ol>
 *
 * <p>💡 Eclipse 단축키:
 *   <code>Alt+Shift+S</code> → Generate Constructor / Getters and Setters / toString()
 * </p>
 */
public class Book {

    // TODO: private 필드 6개 선언
	private String BookCode;
	private int ClassificationId;
	private String author;
	private String name;
	private String publisher;
	private String isReserve;
	
    /** 기본 생성자 — 그대로 사용 */
    public Book() {}

    /**
     * 전체 인자 생성자 — 학생 TODO: 매개변수 6개로 모든 필드 초기화
     */
    public Book(String bookCode, Integer classificationId,
                String author, String name, String publisher, String isReserve) {
        // TODO: 6개 필드 초기화 (this.xxx = xxx)
    	this.BookCode = bookCode;
    	this.ClassificationId = classificationId;
    	this.author = author;
    	this.name = name;
    	this.publisher = publisher;
    	this.isReserve = isReserve;
    }

    // ====== getter / setter — 학생 TODO: 본문 채우기 (시그니처는 변경 금지) ======

	public String getBookCode() {
		return BookCode;
	}

	public void setBookCode(String bookCode) {
		BookCode = bookCode;
	}

	public int getClassificationId() {
		return ClassificationId;
	}

	public void setClassificationId(int classificationId) {
		ClassificationId = classificationId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getIsReserve() {
		return isReserve;
	}

	public void setIsReserve(String isReserve) {
		this.isReserve = isReserve;
	}
	
    /** 학생 TODO: 모든 필드 표시 (디버깅 용) */
	@Override
	public String toString() {
		return "Book[TODO=" + BookCode + ", Classification_Id=" + ClassificationId + ", author=" + author
				+ ", name=" + name + ", publisher=" + publisher + ", isReserve=" + isReserve + "]";
	}




}
