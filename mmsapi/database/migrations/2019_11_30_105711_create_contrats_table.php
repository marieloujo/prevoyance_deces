<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateContratsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('contrats', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->string('numero_contrat')->unique();
            $table->string('garantie');
            $table->string('prime');
            $table->string('duree');
            $table->string('frais_dossier');
            $table->string('date_debut');
            $table->string('date_echeance');
            $table->string('date_effet');
            $table->boolean('fin')->default(false);
            $table->boolean('valider')->default(false);
            $table->string('date_fin')->nullable();
            $table->unsignedInteger('numero_police_assurance')->nullable();

            $table->unsignedBigInteger('client_id');

            $table->unsignedBigInteger('assure_id');

            $table->unsignedBigInteger('marchand_id');

            $table->foreign('marchand_id')->references('id')->on('marchands')->ondelete('cascade');

            $table->foreign('client_id')->references('id')->on('clients')->ondelete('cascade');

            $table->foreign('assure_id')->references('id')->on('assures')->ondelete('cascade');

            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('contrats');
    }
}
