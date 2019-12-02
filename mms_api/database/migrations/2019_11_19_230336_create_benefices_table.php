<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateBeneficesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('benefices', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->string('statut');
            $table->string('taux')->default(0);
            $table->unsignedBigInteger('contrat_id');
            $table->unsignedBigInteger('beneficiaire_id');

            $table->foreign('contrat_id')->references('id')->on('contrats')->ondelete('cascade');
            $table->foreign('beneficiaire_id')->references('id')->on('beneficiaires')->ondelete('cascade');
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
        Schema::dropIfExists('benefices');
    }
}
