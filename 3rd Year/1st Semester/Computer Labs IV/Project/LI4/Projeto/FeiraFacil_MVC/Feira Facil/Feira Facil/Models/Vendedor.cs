using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Feira_Facil.Models
{
    public class Vendedor
    {

        [Key]
        public int Id { get; set; }
        //[ForeignKey("IdEmpresa")]
        public int idEmpresa { get; set; }
        public string nome { get; set; }
		public string contactos { get; set; }
		public string morada { get; set; }
		public string password { get; set; }

	}
}
