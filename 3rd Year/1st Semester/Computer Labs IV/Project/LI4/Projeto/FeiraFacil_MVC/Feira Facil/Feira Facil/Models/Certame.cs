using System.ComponentModel.DataAnnotations;

namespace Feira_Facil.Models
{
    public class Certame
    {

        [Key]
        public int IdCertame { get; set; }
        public int maxStandsCertame { get; set; }
        public string categoraCertame { get; set; }
        public int ativo { get; set; }

    }
}
