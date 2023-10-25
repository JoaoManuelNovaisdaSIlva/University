using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Diagnostics.CodeAnalysis;

namespace Feira_Facil.Models
{
    public class Produto
    {

        [Key]
        public int IdProduto { get; set; }
        //[ForeignKey("Id")]
        public int idVendedor { get; set; }
        //[ForeignKey("IdStand")]
        public int idStand { get; set; }
        [AllowNull]
		public double preco { get; set; }
		public string imagens { get; set; }
        public string nome { get; set; }
        [AllowNull]
        public int stock { get; set; }

	}
}
