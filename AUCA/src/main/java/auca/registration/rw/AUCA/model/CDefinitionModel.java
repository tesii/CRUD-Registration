package auca.registration.rw.AUCA.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "course_definition")
public class CDefinitionModel {
	 @Id
	    private String id; 
	    @Column(name = "course_code")
	    private String course_code; // Code now corresponds to 'code' in the table

	    @Column(name = "name")
	    private String name;

	    @Column(name = "description")
	    private String description;

		public CDefinitionModel() {
			super();
		}

		public CDefinitionModel(String id, String course_code, String name, String description) {
			super();
			this.id = id;
			this.course_code = course_code;
			this.name = name;
			this.description = description;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getCourse_code() {
			return course_code;
		}

		public void setCourse_code(String course_code) {
			this.course_code = course_code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	    
}
