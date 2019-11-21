<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateAssurancesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('assurances', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->unsignedInteger('garantie');
            $table->unsignedInteger('prime');
            $table->string('duree');
            $table->string('date_debut');
            $table->string('date_echeance');
            $table->string('date_effet');
            $table->boolean('fin');
            $table->boolean('valider');
            $table->string('date_fin')->nullable();
            $table->string('portefeuille')->nullable();
            $table->unsignedInteger('numero_police_assurance')->nullable();
            $table->unsignedBigInteger('client_id');

            $table->unsignedBigInteger('assure_id');

            $table->foreign('client_id')
                ->references('id')
                ->on('clients')
                ->ondelete('cascade');
    
            $table->foreign('assure_id')
                ->references('id')
                ->on('assures')
                ->ondelete('cascade');
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
        Schema::dropIfExists('assurances');
    }
}
