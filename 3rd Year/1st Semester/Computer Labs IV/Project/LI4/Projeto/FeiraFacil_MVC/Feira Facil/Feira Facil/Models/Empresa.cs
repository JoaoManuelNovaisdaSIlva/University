using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Feira_Facil.Models
{
    public class Empresa
    {

        [Key]
        public int IdEmpresa { get; set; }
        public string nome { get; set; }
        public int nif { get; set; }
		public string morada { get; set; }
		public string contactos { get; set; }

        public string loginUsername { get; set; }

        public string loginPassword { get; set; }

	}
}
