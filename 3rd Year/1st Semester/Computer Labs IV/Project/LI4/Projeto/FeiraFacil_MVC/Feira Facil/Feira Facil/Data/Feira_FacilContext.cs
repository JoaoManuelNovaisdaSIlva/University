using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Feira_Facil.Models;

namespace Feira_Facil.Data
{
    public class Feira_FacilContext : DbContext
    {
        public Feira_FacilContext (DbContextOptions<Feira_FacilContext> options)
            : base(options)
        {
        }

        public DbSet<Feira_Facil.Models.User> User { get; set; } = default!;

        public DbSet<Feira_Facil.Models.Certame> Certame { get; set; }

        public DbSet<Feira_Facil.Models.Empresa> Empresa { get; set; }

        public DbSet<Feira_Facil.Models.Produto> Produto { get; set; }

        public DbSet<Feira_Facil.Models.Stand> Stand { get; set; }

        public DbSet<Feira_Facil.Models.Vendedor> Vendedor { get; set; }

		public DbSet<Feira_Facil.Models.Admin> Admin { get; set; }

	}
}
