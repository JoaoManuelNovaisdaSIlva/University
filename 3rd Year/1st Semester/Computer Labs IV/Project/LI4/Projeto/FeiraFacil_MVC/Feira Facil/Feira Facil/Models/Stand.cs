using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Feira_Facil.Models
{
    public class Stand
    {

        [Key]
        public int IdStand { get; set; }
        public int idVendedor { get; set; }
        public int idCertame { get; set; }

    }
}
